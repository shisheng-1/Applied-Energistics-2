/*
 * This file is part of Applied Energistics 2.
 * Copyright (c) 2021, TeamAppliedEnergistics, All rights reserved.
 *
 * Applied Energistics 2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Applied Energistics 2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Applied Energistics 2.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */

package appeng.mixins.structure;

import appeng.worldgen.meteorite.MeteoriteStructure;
import com.google.common.collect.ImmutableMap;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * This Mixin will add the structure placement configuration for the meteorite structure to the static final immutable
 * map that contains them. There is currently no Forge hook for this, and registering them during the registry event is
 * already too late.
 * <p>
 * If this is not done, Meteorites spawn every chunk, since that is the default for missing entries.
 */
@Mixin(DimensionStructuresSettings.class)
public class DimensionStructuresSettingsMixin {

    @Shadow
    @Mutable
    private static ImmutableMap<Structure<?>, StructureSeparationSettings> field_236191_b_;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void addMeteoriteSpreadConfig(CallbackInfo ci) {
        field_236191_b_ = ImmutableMap.<Structure<?>, StructureSeparationSettings>builder().putAll(field_236191_b_)
                .put(MeteoriteStructure.INSTANCE, new StructureSeparationSettings(32, 8, 124895654)).build();
    }

}
