package utils

enum PackMode {
    PLAYER(false, false, false),
    TESTER(true, false, false),
    DEVELOPER(true, true, true),
    BUILDER(false, false, true)

    private boolean allowDebug
    private boolean allowDevelop
    private boolean allowBuild

    PackMode(boolean allowDebug, boolean allowDevelop, boolean allowBuild) {
        this.allowDebug = allowDebug
        this.allowDevelop = allowDevelop
        this.allowBuild = allowBuild
    }

    boolean getDebugCapabilities() {
        return isDebug() && allowDebug
    }

    boolean getBuildCapabilites() {
        return allowBuild && CommonUtils.notSurvivalSP()
    }

    boolean getDevelopCapabilites() {
        return allowDevelop
    }

    boolean getExclusiveCapabilities() {
        return allowDevelop || allowDebug || allowBuild
    }

    static PackMode switchPackMode(PackMode packMode) {
        switch (packMode) {
            case PLAYER:
                return TESTER
                break
            case TESTER:
                return DEVELOPER
                break
            case DEVELOPER:
                return BUILDER
                break
            default:
                return PLAYER
        }
    }
}