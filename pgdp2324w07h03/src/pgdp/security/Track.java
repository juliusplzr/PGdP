package pgdp.security;

public class Track {
    private SignalPost[] posts;
    public Track(int size) {
        int trueSize = size <= 0 ? 10 : size;

        this.posts = new SignalPost[trueSize];

        for (int i = 0; i < posts.length - 1; i++) {
            posts[i] = i % 3 == 0 ? new LightPanel(i) : new FlagPost(i);
        }

        posts[posts.length - 1] = new FinishPost(posts.length - 1);
    }

    public SignalPost[] getPosts() {
        return posts;
    }

    public void setPosts(SignalPost[] posts) {
        this.posts = posts;
    }

    public void setAll(String type, boolean up) {
        if (up) {
            for (SignalPost post : posts) {
                post.up(type);
            }
        } else {
            for (SignalPost post : posts) {
                post.down(type);
            }
        }
    }

    public void setRange(String type, boolean up, int start, int end) {
        for (int i = start; i != end; i = (i + 1) % posts.length) {
            if (up) {
                posts[i].up(type);
            } else {
                posts[i].down(type);
            }
        }

        if (up) {
            posts[end].up(type);
        } else {
            posts[end].down(type);
        }
    }

    public void createHazardAt(int start, int end) {
        for (int i = start; i != end; i = (i + 1) % posts.length) {
            posts[i].up("yellow");
        }
        posts[end].up("green");
    }

    public void removeHazardAt(int start, int end) {
        for (int i = start; i != end; i = (i + 1) % posts.length) {
            posts[i].down("danger");
        }
    }

    public void createLappedCarAt(int post) {
        for (int i = 0; i < 4; i++) {
            posts[(post + i) % posts.length].up("blue");
        }
    }

    public void removeLappedCarAt(int post) {
        for (int i = 0; i < 4; i++) {
            posts[(post + i) % posts.length].down("blue");
        }
    }

    public void printStatus() {
        StringBuilder sb = new StringBuilder();
        for (SignalPost p : posts) {
            sb.append(p).append("\n");
        }

        System.out.println(sb.toString());

    }
}
