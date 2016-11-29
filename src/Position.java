/**
 * Created by silasa on 10/11/16.
 */
public class Position {
    private int x;
    private int y;

    public Position(int nX, int nY) {
        x = nX;
        y = nY;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return (position.getX() == x && position.getY() == y);
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "[" + x + "," + y + "]";
    }

    public int getDistance(Position oPos) {
        if (getX() + getY() > oPos.getX() + oPos.getY()) {
            return getX() + getY() - oPos.getX() - oPos.getY();
        } else {
            return oPos.getX() + oPos.getY() - getX() - getY();
        }
    }
}
