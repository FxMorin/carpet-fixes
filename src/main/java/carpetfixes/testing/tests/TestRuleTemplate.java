package carpetfixes.testing.tests;

import mctester.annotation.GameTest;
import mctester.annotation.GameTestTemplate;
import mctester.common.test.creation.GameTestHelper;
import mctester.common.test.creation.TestConfig;
import mctester.common.test.exceptions.GameTestAssertException;
import mctester.common.util.GameTestUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NoteBlock;
import net.minecraft.structure.Structure;
import net.minecraft.test.PositionedException;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.stream.Stream;

public class TestRuleTemplate {

    @GameTestTemplate(name = "test_rule")
    public static Stream<TestConfig> testFromStructure(String structureName) {
        TestConfig testConfig = new TestConfig(TestRuleTemplate::test_rule)
                .structureName(structureName)
                .structurePlaceCooldown(6) //All updates negated so this is just in-case
                .timeout(360); //18 second timeout
        return Stream.of(testConfig);
    }

    private static boolean isEarlyTriggerBlock(BlockState blockState) {
        return blockState.isOf(Blocks.LIME_GLAZED_TERRACOTTA);
    }

    private static boolean isFinishedTriggerBlock(BlockState blockState) {
        return blockState.isOf(Blocks.RED_GLAZED_TERRACOTTA);
    }

    private static boolean isSuccessBlock(BlockState blockState) {
        return blockState.isOf(Blocks.LIME_WOOL);
    }

    private static boolean isFailureBlock(BlockState blockState) {
        return blockState.isOf(Blocks.RED_WOOL);
    }

    private static void onFinish(GameTestHelper helper) {
        // Turn Red Glazed Terracotta to redstone block.
        // This is the finish trigger, happens before finishing
        GameTestUtil.streamPositions(helper.gameTest).forEach(pos -> {
            BlockState blockState = helper.gameTest.getWorld().getBlockState(pos);
            if (isFinishedTriggerBlock(blockState))
                helper.gameTest.getWorld().setBlockState(pos, Blocks.REDSTONE_BLOCK.getDefaultState());
        });
    }

    /**
     * A test function that can be used to create tests with a simple redstone interface.
     */
    @GameTest
    public static void test_rule(GameTestHelper helper) {
        ArrayList<BlockPos> successBlocks = new ArrayList<>();
        ArrayList<BlockPos> failureBlocks = new ArrayList<>();


        GameTestUtil.streamPositions(helper.gameTest).forEach(blockPos -> {
            BlockState blockState = helper.gameTest.getWorld().getBlockState(blockPos);
            if (blockState.isOf(Blocks.LIME_WOOL)) successBlocks.add(blockPos.toImmutable());
            if (blockState.isOf(Blocks.RED_WOOL)) failureBlocks.add(blockPos.toImmutable());
        });
        if (successBlocks.isEmpty()) {
            throw new GameTestAssertException("Expected success condition blocks anywhere inside the test. " +
                            "test_rule require at least a lime wool block for the success condition");
        }

        //Turn Lime Glazed Terracotta to redstone block. This is the early trigger, happens before anything else
        GameTestUtil.streamPositions(helper.gameTest).forEach(blockPos -> {
            BlockState blockState = helper.gameTest.getWorld().getBlockState(blockPos);
            if (isEarlyTriggerBlock(blockState))
                helper.gameTest.getWorld().setBlockState(blockPos, Blocks.REDSTONE_BLOCK.getDefaultState());
        });

        //Replace all red terracotta with redstone block on the first tick
        helper.addAction(1, (h) -> {
            GameTestUtil.streamPositions(h.gameTest).forEach(blockPos -> {
                BlockState blockState = h.gameTest.getWorld().getBlockState(blockPos);
                if (blockState.isOf(Blocks.RED_TERRACOTTA))
                    h.gameTest.getWorld().setBlockState(blockPos, Blocks.REDSTONE_BLOCK.getDefaultState());
            });
        });

        // Succeed when any powered note block is on top of a success condition block.
        // Assume the success condition block doesn't move etc.
        helper.succeedWhen(
                helper1 -> {
                    // Always check the failure condition blocks before the success conditions.
                    // Failed tests throw exceptions.
                    if (
                            failureBlocks.stream().anyMatch(blockPos -> {
                                BlockState blockState = helper1.gameTest.getWorld().getBlockState(blockPos.up());
                                return blockState.isOf(Blocks.NOTE_BLOCK) && blockState.get(NoteBlock.POWERED) &&
                                        isFailureBlock(helper1.gameTest.getWorld().getBlockState(blockPos));
                            })) {
                        BlockPos blockPos = helper1.gameTest.getPos();
                        BlockPos absolutePos = successBlocks.get(0);
                        BlockPos relativePos = Structure.transformAround(
                                absolutePos,
                                BlockMirror.NONE,
                                GameTestUtil.getInverse(helper1.gameTest.getRotation()),
                                blockPos
                        ).add(-blockPos.getX(), -blockPos.getY(), -blockPos.getZ());
                        onFinish(helper);
                        throw new PositionedException(
                                "Failure condition was met with powered noteblock on top of a failure condition block",
                                absolutePos,
                                relativePos,
                                helper1.currTick
                        );
                    }
                    return successBlocks.stream().anyMatch(blockPos -> {
                        BlockState blockState = helper1.gameTest.getWorld().getBlockState(blockPos.up());
                        return blockState.isOf(Blocks.NOTE_BLOCK) && blockState.get(NoteBlock.POWERED) &&
                                isSuccessBlock(helper1.gameTest.getWorld().getBlockState(blockPos));
                    });
                },
                helper1 -> {
                    BlockPos blockPos = helper1.gameTest.getPos();
                    BlockPos absolutePos = successBlocks.get(0);
                    BlockPos relativePos = Structure.transformAround(
                            absolutePos,
                            BlockMirror.NONE,
                            GameTestUtil.getInverse(helper1.gameTest.getRotation()),
                            blockPos
                    ).add(-blockPos.getX(), -blockPos.getY(), -blockPos.getZ());
                    onFinish(helper);
                    return new PositionedException(
                            "Expected powered noteblock on top of an success condition block. For example",
                            absolutePos,
                            relativePos,
                            helper1.currTick
                    );
                }
        );
    }
}
