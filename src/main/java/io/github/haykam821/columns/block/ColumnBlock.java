package io.github.haykam821.columns.block;

import io.github.haykam821.columns.Main;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.EntityContext;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class ColumnBlock extends Block implements Waterloggable {
	public static final BooleanProperty UP = Properties.UP;
	public static final BooleanProperty DOWN = Properties.DOWN;
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

	public static final VoxelShape UP_SHAPE = Block.createCuboidShape(0, 13, 0, 16, 16, 16);
	public static final VoxelShape CENTER_SHAPE = Block.createCuboidShape(4, 0, 4, 12, 16, 12);
	public static final VoxelShape DOWN_SHAPE = Block.createCuboidShape(0, 0, 0, 16, 3, 16);

	public ColumnBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getStateManager().getDefaultState().with(UP, true).with(DOWN, true).with(WATERLOGGED, false));
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext context) {
		if (state.get(UP) && state.get(DOWN)) {
			return VoxelShapes.union(UP_SHAPE, CENTER_SHAPE, DOWN_SHAPE);
		} else if (state.get(UP)) {
			return VoxelShapes.union(UP_SHAPE, CENTER_SHAPE);
		} else if (state.get(DOWN)) {
			return VoxelShapes.union(CENTER_SHAPE, DOWN_SHAPE);
		} else {
			return CENTER_SHAPE;
		}
	}

	public boolean hasEndInDirection(World world, BlockPos pos, Direction direction) {
		BlockPos targetPos = pos.offset(direction);
		BlockState targetState = world.getBlockState(targetPos);
		return !Main.COLUMNS.contains(targetState.getBlock());
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext context) {
		World world = context.getWorld();
		BlockPos pos = context.getBlockPos();

		boolean shouldConnectUp = this.hasEndInDirection(world, pos, Direction.UP);
		boolean shouldConnectDown = this.hasEndInDirection(world, pos, Direction.DOWN);
		boolean shouldBeWaterlogged = world.getFluidState(pos).getFluid() == Fluids.WATER;

		return this.getDefaultState().with(UP, shouldConnectUp).with(DOWN, shouldConnectDown).with(WATERLOGGED, shouldBeWaterlogged);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction towards, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos) {
		if (state.get(WATERLOGGED)) {
			world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}
		
		if (towards == Direction.UP || towards == Direction.DOWN) {
			boolean shouldConnect = this.hasEndInDirection(world.getWorld(), pos, towards);
			return state.with(towards == Direction.UP ? UP : DOWN, shouldConnect);
		}
		return state;
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getDefaultState() : Fluids.EMPTY.getDefaultState();
	}

	@Override
	public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(UP, DOWN, WATERLOGGED);
	}
}