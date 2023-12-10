package pgdp.security;

public class LightPanel extends SignalPost {
    public LightPanel(int postNumber) {
        super(postNumber);
    }

    public boolean up(String type) {
        int nextLevel = switch (type) {
            case "" -> 0;
            case "green", "blue" -> 1;
            case "yellow" -> 2;
            case "doubleYellow", "[SC]" -> 3;
            case "red" -> 4;
            case "end" -> 5;
            default -> -1;
        };

        if (nextLevel < this.getLevel()) {
            return false;
        } else if (nextLevel == this.getLevel()) {
            String currentDepiction = this.getDepiction();

            if (currentDepiction.equals(type)) {
                return false;
            }

            if (this.getDepiction().equals("green") || this.getDepiction().equals("doubleYellow")) {
                this.setDepiction(type);
            }

            return !currentDepiction.equals(getDepiction());
        }

        super.setDepiction(type.equals("end") ? "yellow" : type);
        super.setLevel(nextLevel);
        return true;
    }

    public boolean down(String type) {
        switch (type) {
            case "clear" -> {
                if (getLevel() != 0) {
                    setDepiction("");
                    setLevel(0);
                    return true;
                }

                return false;
            }

            case "green" -> {
                if (getDepiction().equals("green")) {
                    setDepiction("");
                    setLevel(0);
                    return true;
                }
                return false;
            }

            case "blue" -> {
                if (getDepiction().equals("blue")) {
                    setDepiction("");
                    setLevel(0);
                    return true;
                }

                return false;
            }
            case "danger" -> {
                String currentDepiction = getDepiction();
                if (getLevel() != 5 && (currentDepiction.contains("yellow") || currentDepiction.contains("doubleYellow")
                        || currentDepiction.contains("red") || currentDepiction.contains("[SC]"))) {
                    setDepiction("green");
                    setLevel(1);

                    return true;
                }
                return false;
            }
            default -> { return false; }
        }

    }

    @Override
    public String toString() {
        if (this.getLevel() == 0) {
           return "Signal post " + this.getPostNumber() + " of type light panel is in level " +
                   this.getLevel() + " and is switched off";
        }

        return "Signal post " + this.getPostNumber()  + " of type light panel is in level " +
                this.getLevel() + " and is blinking "  + Helper.changeColors(this.getDepiction());
    }
}