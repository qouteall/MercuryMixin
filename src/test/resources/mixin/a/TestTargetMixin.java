/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(hj.class)
public abstract class TestTargetMixin {

    @Shadow
    private String thhh;

    @Shadow
    @Final
    private String agh;

    @Shadow
    @Mutable
    private int shadow$yu;

    @Shadow
    public abstract String jei();

    @Shadow(prefix="test$")
    public abstract int test$julp();

    @Overwrite
    public void gyhu() {
        System.out.println("Hello, world!");
    }

    @Inject(method = {"hhj", "jjjj"}, at = @At("HEAD"))
    public void onStart(final CallbackInfo callbackInfo) {
        System.out.println("Hello, world!");
    }

    @Inject(method = {"hhj()V", "jjjj"}, at = @At("HEAD"))
    public void onStart2(final CallbackInfo callbackInfo) {
        System.out.println("Hello, world!");
    }

    @Inject(method = "hhj", at = @At(value = "INVOKE", target = "Lhj;gyhu()V"))
    public void inject(final CallbackInfo callbackInfo) {
        System.out.println("Hello from injection!");
    }

    @Inject(method = "hhj", at = {
            @At("HEAD"),
            @At(value = "INVOKE", target = "Lhj;julp()I"),
            @At(value = "INVOKE_ASSIGN", target = "Lhj;gyhu()V")
    })
    public void injectMultiple(final CallbackInfo callbackInfo) {
        System.out.println("Hello from injection!");
    }

    @Inject(method = "hhj", at = @At(value = "NEW", target = "hj"))
    public void injectNew(final CallbackInfo callbackInfo) {
        System.out.println("Hello from new injection!");
    }

}
