package io.github.haykam821.columns.block;

import com.mojang.serialization.MapCodec;

import io.github.haykam821.columns.Main;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ColumnBlock extends Block implements SimpleWaterloggedBlock {
	public static final BooleanProperty UP = BlockStateProperties.UP;
	public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public static final VoxelShape UP_SHAPE = Block.box(0, 13, 0, 16, 16, 16);
	public static final VoxelShape CENTER_SHAPE = Block.box(4, 0, 4, 12, 16, 12);
	public static final VoxelShape DOWN_SHAPE = Block.box(0, 0, 0, 16, 3, 16);

	private static final VoxelShape UP_CENTER_DOWN_SHAPE = Shapes.or(UP_SHAPE, CENTER_SHAPE, DOWN_SHAPE);
	private static final VoxelShape UP_CENTER_SHAPE = Shapes.or(UP_SHAPE, CENTER_SHAPE);
	private static final VoxelShape CENTER_DOWN_SHAPE = Shapes.or(CENTER_SHAPE, DOWN_SHAPE);

	public static final MapCodec<ColumnBlock> CODEC = Block.simpleCodec(ColumnBlock::new);

	public ColumnBlock(Properties settings) {
		super(settings);
		this.registerDefaultState(this.getStateDefinition().any().setValue(UP, true).setValue(DOWN, true).setValue(WATERLOGGED, false));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext context) {
		if (state.getValue(UP) && state.getValue(DOWN)) {
			return UP_CENTER_DOWN_SHAPE;
		} else if (state.getValue(UP)) {
			return UP_CENTER_SHAPE;
		} else if (state.getValue(DOWN)) {
			return CENTER_DOWN_SHAPE;
		} else {
			return CENTER_SHAPE;
		}
	}

	public boolean hasEndInDirection(LevelReader world, BlockPos pos, Direction direction) {
		BlockPos targetPos = pos.relative(direction);
		BlockState targetState = world.getBlockState(targetPos);
		return !targetState.is(Main.COLUMNS_BLOCK_TAG);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Level world = context.getLevel();
		BlockPos pos = context.getClickedPos();

		boolean shouldConnectUp = this.hasEndInDirection(world, pos, Direction.UP);
		boolean shouldConnectDown = this.hasEndInDirection(world, pos, Direction.DOWN);
		boolean shouldBeWaterlogged = world.getFluidState(pos).getType() == Fluids.WATER;

		return this.defaultBlockState().setValue(UP, shouldConnectUp).setValue(DOWN, shouldConnectDown).setValue(WATERLOGGED, shouldBeWaterlogged);
	}

	@Override
	protected BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction towards, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
		if (state.getValue(WATERLOGGED)) {
			tickView.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		}
		
		if (towards == Direction.UP || towards == Direction.DOWN) {
			boolean shouldConnect = this.hasEndInDirection(world, pos, towards);
			return state.setValue(towards == Direction.UP ? UP : DOWN, shouldConnect);
		}
		return state;
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.defaultFluidState() : Fluids.EMPTY.defaultFluidState();
	}

	@Override
	public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(UP, DOWN, WATERLOGGED);
	}

	@Override
	protected MapCodec<? extends ColumnBlock> codec() {
		return CODEC;
	}
}