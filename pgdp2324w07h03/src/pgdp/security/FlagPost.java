package pgdp.security;


public class FlagPost extends SignalPost {
    public FlagPost(int postNumber) {
        super(postNumber);
    }

    public boolean up(String type) {
        int nextLevel = switch (type) {
            case "green", "blue" -> 1;
            case "yellow" -> 2;
            case "doubleYellow", "[SC]" -> 3;
            case "red" -> 4;
            case "end" -> 5;
            default -> -1;
        };

        String currentDepiction = this.getDepiction();
        int currentLevel = this.getLevel();

        if (nextLevel < currentLevel) {
            return false;
        }

        if (nextLevel == currentLevel) {
            if (!type.equals("end") && !currentDepiction.contains(type)) {
                boolean intermediateStep = currentDepiction.equals("blue") || currentDepiction.equals("green");

                setDepiction(intermediateStep ? "green/blue" : currentDepiction + "/" + type);

                return true;
            }

            return false;
        }

        super.setDepiction(type.contains("[SC]") ? "doubleYellow/[SC]" : type.equals("end") ? "green/yellow/red/blue" : type);
        super.setLevel(nextLevel);
        return true;
    }

    public boolean down(String type) {
        String currentDepiction = this.getDepiction();
        int currentLevel = this.getLevel();

        switch (type) {
            case "clear" -> {
                if (currentLevel != 0) {
                    setDepiction("");
                    setLevel(0);
                    return true;
                }
            }

            case "green" -> {
                if (currentDepiction.equals("green")) {
                    setDepiction("");
                    setLevel(0);
                    return true;
                }

                if (currentDepiction.equals("green/blue") && currentLevel == 1) {
                    setDepiction("blue");
                    return true;
                }
            }

            case "blue" -> {
                if (currentDepiction.equals("blue")) {
                    setDepiction("");
                    setLevel(0);
                    return true;
                }

                if (currentDepiction.equals("green/blue") && currentLevel == 1) {
                    setDepiction("green");
                    return true;
                }

            }

            case "danger" -> {
                if (getLevel() != 5 && (currentDepiction.equals("yellow") || currentDepiction.contains("doubleYellow") || currentDepiction.equals("red"))) {
                    setDepiction("green");
                    setLevel(1);
                    return true;
                }
            }

            default -> { return false; }
        }

        return false;
    }

    @Override
    public String toString() {
        return "Signal post " + getPostNumber() + " of type  flag post  is in level " + getLevel() + " and is "
                + (getLevel() == 0 ? " doing nothing" : " waving  " + Helper.changeColors(getDepiction()));
    }
}
