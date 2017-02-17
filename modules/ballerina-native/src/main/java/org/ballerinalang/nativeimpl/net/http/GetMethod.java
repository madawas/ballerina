/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.ballerinalang.nativeimpl.net.http;

import org.ballerinalang.bre.Context;
import org.ballerinalang.model.types.TypeEnum;
import org.ballerinalang.model.values.BString;
import org.ballerinalang.model.values.BValue;
import org.ballerinalang.natives.AbstractNativeFunction;
import org.ballerinalang.natives.annotations.Argument;
import org.ballerinalang.natives.annotations.BallerinaFunction;
import org.ballerinalang.natives.annotations.ReturnType;

import static org.ballerinalang.nativeimpl.connectors.http.Constants.HTTP_METHOD;

/**
 * Get HTTP Method from the message.
 */
@BallerinaFunction(
        packageName = "ballerina.net.http",
        functionName = "getMethod",
        args = {@Argument(name = "m", type = TypeEnum.MESSAGE)},
        returnType = {@ReturnType(type = TypeEnum.STRING)},
        isPublic = true
)
public class GetMethod extends AbstractNativeFunction {
    String httpMethod;
    public BValue[] execute(Context ctx) {
        if (ctx.getCarbonMessage().getProperty(HTTP_METHOD) != null) {
            httpMethod = ctx.getCarbonMessage().getProperty(HTTP_METHOD).toString();
        }
        return getBValues(new BString(httpMethod));
    }
}