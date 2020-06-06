/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.cadixdev.mercury.mixin.annotation;

import org.cadixdev.bombe.type.MethodDescriptor;

import java.util.Optional;

/**
 * Method target can either be a method name or a method signature
 *
 * @author Jadon Fowler
 */
public class MethodTarget {

    private final String methodName;
    private final MethodDescriptor methodDescriptor;
    private final String methodClass;

    public MethodTarget(final String methodName) {
        this.methodName = methodName;
        this.methodDescriptor = null;
        this.methodClass = null;
    }

    public MethodTarget(final String methodName, final MethodDescriptor methodDescriptor, final String methodClass) {
        this.methodName = methodName;
        this.methodDescriptor = methodDescriptor;
        this.methodClass = methodClass;
    }

    public static MethodTarget of(final String target) {
        int braceIndex = target.indexOf('(');
        if (braceIndex >= 0) {
            String front = target.substring(0, braceIndex);
            int semiColonIndex = front.indexOf(';');
            if (semiColonIndex == -1) {
                return new MethodTarget(front, MethodDescriptor.of(target.substring(braceIndex)), null);
            }
            else {
                return new MethodTarget(
                    front.substring(semiColonIndex + 1, braceIndex),
                    MethodDescriptor.of(target.substring(braceIndex)),
                    front.substring(0, semiColonIndex)
                );
            }
        }
        return new MethodTarget(target);
    }

    public String getMethodName() {
        return this.methodName;
    }

    public Optional<String> getMethodClass() {
        return Optional.ofNullable(methodClass);
    }

    public Optional<MethodDescriptor> getMethodDescriptor() {
        return Optional.ofNullable(this.methodDescriptor);
    }

    public boolean isConstructor() {
        return methodName.equals("<init>");
    }

    @Override
    public String toString() {
        return "MethodTarget{" +
            "methodName='" + this.methodName + '\'' +
            ", methodDescriptor=" + this.methodDescriptor +
            '}';
    }

}
