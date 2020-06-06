/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.cadixdev.mercury.mixin.annotation;

import org.eclipse.jdt.core.dom.IAnnotationBinding;
import org.eclipse.jdt.core.dom.IMemberValuePairBinding;

import java.util.Objects;
import java.util.Optional;

/**
 * A container for data held in the {@code @At} annotation.
 *
 * @author Jadon Fowler
 */
public class AtData {

    public static enum InjectionPoint {
        INVOKE, FIELD, NEW, OTHER
    }

    // @At(value = "", target = "")
    public static AtData from(final IAnnotationBinding binding) {
        InjectionPoint injectionPoint = InjectionPoint.OTHER;
        String className = null;
        MethodTarget target = null;

        for (final IMemberValuePairBinding pair : binding.getDeclaredMemberValuePairs()) {
            if (Objects.equals("value", pair.getName())) {
                String atType = (String) pair.getValue();
                if (Objects.equals("INVOKE", atType)) {
                    injectionPoint = InjectionPoint.INVOKE;
                }
                else if (Objects.equals("FIELD", atType)) {
                    injectionPoint = InjectionPoint.FIELD;
                }
                else if (Objects.equals("NEW", atType)) {
                    injectionPoint = InjectionPoint.NEW;
                }
            }
            else if (Objects.equals("target", pair.getName())) {
                final String combined = (String) pair.getValue();

                int semiIndex = combined.indexOf(';');
                if (semiIndex >= 0) {
                    className = combined.substring(1, semiIndex);
                    target = MethodTarget.of(combined.substring(semiIndex + 1));
                }
                else {
                    // it's just the class name, probably a NEW
                    className = combined;
                }
            }
        }

        return new AtData(injectionPoint, className, target);
    }

    private final InjectionPoint injectionPoint;
    private final String className;
    private final MethodTarget target;

    public AtData(InjectionPoint injectionPoint, String className, MethodTarget target) {
        this.injectionPoint = injectionPoint;
        this.className = className;
        this.target = target;
    }

    public InjectionPoint getInjectionPoint() {
        return this.injectionPoint;
    }

    public Optional<String> getClassName() {
        return Optional.ofNullable(this.className);
    }

    public Optional<MethodTarget> getTarget() {
        return Optional.ofNullable(this.target);
    }

    @Override
    public String toString() {
        return "AtData{" +
            "injectionPoint='" + this.injectionPoint + '\'' +
            ", className='" + this.className + '\'' +
            ", target=" + this.target +
            '}';
    }

}
