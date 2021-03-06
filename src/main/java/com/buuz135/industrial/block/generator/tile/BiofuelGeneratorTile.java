package com.buuz135.industrial.block.generator.tile;

import com.buuz135.industrial.block.tile.IndustrialGeneratorTile;
import com.buuz135.industrial.module.ModuleCore;
import com.buuz135.industrial.module.ModuleGenerator;
import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.component.fluid.FluidTankComponent;
import com.hrznstudio.titanium.component.fluid.SidedFluidTankComponent;
import com.hrznstudio.titanium.component.progress.ProgressBarComponent;
import net.minecraft.item.DyeColor;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;

public class BiofuelGeneratorTile extends IndustrialGeneratorTile<BiofuelGeneratorTile> {

    @Save
    private SidedFluidTankComponent<BiofuelGeneratorTile> biofuel;

    public BiofuelGeneratorTile() {
        super(ModuleGenerator.BIOFUEL_GENERATOR);
        addTank(biofuel = (SidedFluidTankComponent<BiofuelGeneratorTile>) new SidedFluidTankComponent<BiofuelGeneratorTile>("biofuel", 4000, 43, 20, 0).
                setColor(DyeColor.PURPLE).
                setComponentHarness(this).
                setTankAction(FluidTankComponent.Action.FILL).
                setValidator(fluidStack -> fluidStack.getFluid().isEquivalentTo(ModuleCore.BIOFUEL.getSourceFluid()))
        );
    }

    @Override
    public int consumeFuel() {
        if (biofuel.getFluidAmount() > 0) {
            biofuel.drainForced(1, IFluidHandler.FluidAction.EXECUTE);
            return 4;
        }
        return 0;
    }

    @Override
    public boolean canStart() {
        return biofuel.getFluidAmount() > 0;
    }

    @Override
    public int getEnergyProducedEveryTick() {
        return 160;
    }

    @Override
    public ProgressBarComponent getProgressBar() {
        return new ProgressBarComponent(30, 20, 0, 100)
                .setComponentHarness(this)
                .setBarDirection(ProgressBarComponent.BarDirection.VERTICAL_UP)
                .setColor(DyeColor.CYAN);
    }

    @Override
    public int getEnergyCapacity() {
        return 1_000_000;
    }

    @Override
    public int getExtractingEnergy() {
        return 500;
    }

    @Nonnull
    @Override
    public BiofuelGeneratorTile getSelf() {
        return this;
    }
}
