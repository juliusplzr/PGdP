package pgdp.security;

import static pgdp.security.Helper.*;

public class FinishPost extends FlagPost {
    public FinishPost(int postNumber) {
        super(postNumber);
    }

    @Override
    public boolean up(String type) {
        if (type.contains("end") && getLevel() != 5) {
            setDepiction("chequered");
            setLevel(5);
            return true;
        }
        return super.up(type);
    }

    @Override
    public String toString() {
        return "Signal post " + getPostNumber() + " of type finish post is in level " + getLevel() + " and is "
                + (getLevel() == 0 ? " doing nothing" : " waving  " + changeColors(getDepiction()));
    }
}
