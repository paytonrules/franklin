package tests.mocks;

public class ServerMocket {
    private Mocket mocket;
    private boolean closed = false;

    public ServerMocket(Mocket mocket) {
        this.mocket = mocket;
    }

    public Mocket accept() {
        closed = true;
        return mocket;
    }

    public void close() {
        closed = true;
    }

    public boolean isBound() {
        return true;
    }

    public boolean isClosed() {
        return closed;
    }
}
