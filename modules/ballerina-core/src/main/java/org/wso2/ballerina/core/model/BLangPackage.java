/*
*  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing,
*  software distributed under the License is distributed on an
*  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
*  KIND, either express or implied.  See the License for the
*  specific language governing permissions and limitations
*  under the License.
*/
package org.wso2.ballerina.core.model;

import org.wso2.ballerina.core.model.symbols.BLangSymbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@code BLangPackage} represents a Ballerina package.
 *
 * @since 0.8.0
 */
public class BLangPackage implements SymbolScope, BLangSymbol, Node {
    private String pkgPath;
    private SymbolName symbolName;

    private List<CompilationUnit> compilationUnitList = new ArrayList<>();
    private BallerinaFile[] ballerinaFiles;
    private SymbolName[] dependentPkgSymNames;
    private List<BLangPackage> dependentPkgs = new ArrayList<>();

    // Scope related variables
    private SymbolScope enclosingScope;
    private Map<SymbolName, BLangSymbol> symbolMap = new HashMap<>();

    private boolean symbolsDefined = false;
    private PackageRepository pkgRepo;

    public BLangPackage(BLangProgram programScope) {
        this.enclosingScope = programScope;
        symbolMap = new HashMap<>();
    }

    public BLangPackage(GlobalScope golbalScope) {
        this.enclosingScope = golbalScope;
        symbolMap = new HashMap<>();
    }
    
    public void setPackagePath(String pkgPath) {
        this.pkgPath = pkgPath;
        this.symbolName = new SymbolName(pkgPath);
    }

    public BallerinaFile[] getBallerinaFiles() {
        return ballerinaFiles;
    }

    public void setBallerinaFiles(BallerinaFile[] ballerinaFiles) {
        this.ballerinaFiles = ballerinaFiles;
    }

    public SymbolName[] getDependentPackageNames() {
        return dependentPkgSymNames;
    }

    public void setDependentPackageNames(SymbolName[] dependentPkgNames) {
        this.dependentPkgSymNames = dependentPkgNames;
    }

    public CompilationUnit[] getCompilationUntis() {
        return compilationUnitList.toArray(new CompilationUnit[compilationUnitList.size()]);
    }

    public void addDependentPackage(BLangPackage bLangPackage) {
        this.dependentPkgs.add(bLangPackage);
    }

    public BLangPackage[] getDependentPackages() {
        return dependentPkgs.toArray(new BLangPackage[dependentPkgs.size()]);
    }

    public boolean isSymbolsDefined() {
        return symbolsDefined;
    }

    public void setSymbolsDefined(boolean symbolsDefined) {
        this.symbolsDefined = symbolsDefined;
    }

    public PackageRepository getPackageRepository() {
        return pkgRepo;
    }

    public void setPackageRepository(PackageRepository pkgRepo) {
        this.pkgRepo = pkgRepo;
    }


    // Methods in the SymbolScope interface

    @Override
    public ScopeName getScopeName() {
        return ScopeName.PACKAGE;
    }

    @Override
    public SymbolScope getEnclosingScope() {
        return enclosingScope;
    }

    @Override
    public void define(SymbolName name, BLangSymbol symbol) {
        symbolMap.put(name, symbol);
        compilationUnitList.add((CompilationUnit) symbol);
    }

    @Override
    public BLangSymbol resolve(SymbolName name) {
        return resolve(symbolMap, name);
    }

    public BLangSymbol resolveMembers(SymbolName name) {
        return symbolMap.get(name);
    }


    // Methods in the BLangSymbol interface

    @Override
    public String getName() {
        return pkgPath;
    }

    @Override
    public String getPackagePath() {
        return pkgPath;
    }

    @Override
    public boolean isPublic() {
        return false;
    }

    @Override
    public boolean isNative() {
        return false;
    }

    @Override
    public SymbolName getSymbolName() {
        return symbolName;
    }

    @Override
    public SymbolScope getSymbolScope() {
        return null;
    }


    // Methods in the Node interface

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public NodeLocation getNodeLocation() {
        return null;
    }
}
