package ru.igor17.doublependulum.representation;

import lombok.Getter;
import lombok.Setter;
import ru.igor17.doublependulum.model.Dot;

import java.awt.Color;
import java.util.ArrayList;

import static ru.igor17.doublependulum.App.processingRef;


@Getter
@Setter
public class TimeGraph {
    public enum Mode {
        SCROLLING, STATIONARY
    }

    private String title;

    private int originX;
    private int originY;

    private int dimX;
    private int dimY;

    private int capacity;

    private boolean filled;

    private final Mode mode;
    private final boolean renderScale;
    private final boolean renderTitle;
    private boolean integer;

    private ArrayList<Dot> dots;

    private ScaleSynchronizer scaleSynchronizer;

    private double maxY;
    private double minY;

    private int textSize;

    //Colors

    Color plainCl;
    Color borderCl;
    Color dotCl;
    Color lineCl;
    Color levelLineCl;
    Color valueTextCl;
    Color titleTextCl;
    Color scaleTextCl;

    TimeGraph() {
        this.title = null;
        this.originX = 0;
        this.originY = 0;
        this.dimX = 200;
        this.dimY = 100;
        this.capacity = 0;
        this.mode = Mode.SCROLLING;
        this.renderScale = true;
        this.renderTitle = true;
        this.integer = false;
        this.dots = new ArrayList<>();
        this.scaleSynchronizer = null;
        this.maxY = Double.MIN_VALUE;
        this.minY = Double.MAX_VALUE;
        this.textSize = 12;
    }

    TimeGraph(int capacity) {
        this();
        this.capacity = capacity;
    }

    TimeGraph(int dimX, int dimY, int capacity) {
        this(capacity);
        this.dimX = dimX;
        this.dimY = dimY;
    }

    void setOrigin(int originX, int originY) {
        this.originX = originX;
        this.originY = originY;
    }

    public void setInteger(boolean integer) {
        this.integer = integer;
    }

    public void setScaleSynchronizer(ScaleSynchronizer scaleSynchronizer) {
        this.scaleSynchronizer = scaleSynchronizer;
    }

    public void setMinY(double minY) {
        this.minY = minY;
    }

    public void setMaxY(double maxY) {
        this.maxY = maxY;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setPlainCl(Color plainCl) {
        this.plainCl = plainCl;
    }

    public void setBorderCl(Color borderCl) {
        this.borderCl = borderCl;
    }

    public void setDotCl(Color dotCl) {
        this.dotCl = dotCl;
    }

    public void setLineCl(Color lineCl) {
        this.lineCl = lineCl;
    }

    public void setLevelLineCl(Color levelLineCl) {
        this.levelLineCl = levelLineCl;
    }

    public void setValueTextCl(Color valueTextCl) {
        this.valueTextCl = valueTextCl;
    }

    public void setTitleTextCl(Color titleTextCl) {
        this.titleTextCl = titleTextCl;
    }

    public void setScaleTextCl(Color scaleTextCl) {
        this.scaleTextCl = scaleTextCl;
    }

    void clear() {
        this.dots = new ArrayList<>();
        this.maxY = Double.MIN_VALUE;
        this.minY = Double.MAX_VALUE;
        this.filled = false;
    }
    void addValue(double val) {

        Dot newDot = new Dot(processingRef.millis() / 1000., val);

        if(filled) {
            if(mode == Mode.STATIONARY) return;
            else if(mode == Mode.SCROLLING) removeFirstDot();
        }

        this.dots.add(newDot);

        if(this.dots.size() == capacity) filled = true;

        if(this.maxY < val) {
            this.maxY = val;
            if(this.scaleSynchronizer != null) this.scaleSynchronizer.syncScale();
        }
        if(this.minY > val) {
            this.minY = val;
            if(this.scaleSynchronizer != null) this.scaleSynchronizer.syncScale();
        }
    }

    void removeFirstDot() {
        Dot firstDot = this.dots.get(0);
        double firstY = firstDot.getY();
        this.dots.remove(0);
        if(firstY == this.minY) {
            updateMinY();
        }
        if(firstY == this.maxY) {
            updateMaxY();
        }
    }

    void updateMinY() {
        double newMinY = this.maxY;

        for (Dot dot : this.dots) {
            double y = dot.getY();
            if (y < newMinY) {
                newMinY = y;
            }
        }

        this.minY = newMinY;
        if(this.scaleSynchronizer != null) this.scaleSynchronizer.syncScale();
    }

    void updateMaxY() {
        double newMaxY = this.minY;

        for (Dot dot : this.dots) {
            double y = dot.getY();
            if (y > newMaxY) {
                newMaxY = y;
            }
        }

        this.maxY = newMaxY;
        if(this.scaleSynchronizer != null) this.scaleSynchronizer.syncScale();

    }

    Dot calcAbsCoordinates(int index) {
        Dot dot = this.dots.get(index);
        double absoluteX;
        if (index == 0) {
            absoluteX = this.originX;
        }
        else {
            absoluteX = this.originX + this.dimX *
                    ((dot.getX() - this.dots.get(0).getX())
                            / (this.dots.get(this.dots.size() - 1).getX() - this.dots.get(0).getX()));
        }
        double absoluteY;
        if(this.minY != this.maxY) {
            absoluteY = this.originY + this.dimY * (1 - 0.8 * (dot.getY() - this.minY) / (this.maxY - this.minY));
        }
        else {
            absoluteY = this.originY + this.dimY;
        }
        return new Dot(absoluteX, absoluteY);
    }


    void renderPlain() {
        processingRef.strokeWeight(1);
        processingRef.stroke(this.borderCl.getRGB(), this.borderCl.getAlpha());
        processingRef.fill(this.plainCl.getRGB(), this.plainCl.getAlpha());
        processingRef.rect(this.originX, this.originY, this.dimX, this.dimY);
    }

    void renderDotsLine() {

        Dot absoluteCoordinates;
        Dot previousAbsoluteCoordinates = new Dot();

        for(int dotIdx = 0; dotIdx < this.dots.size(); dotIdx++) {
            absoluteCoordinates = calcAbsCoordinates(dotIdx);

            processingRef.strokeWeight(1);
            processingRef.stroke(this.dotCl.getRGB());
            processingRef.fill(this.dotCl.getRGB());
            processingRef.circle((float)absoluteCoordinates.getX(), (float)absoluteCoordinates.getY(), 2);

            if(dotIdx != 0) {
                processingRef.strokeWeight(1);
                processingRef.stroke(this.lineCl.getRGB());

                processingRef.line((float)previousAbsoluteCoordinates.getX(), (float)previousAbsoluteCoordinates.getY(),
                        (float)absoluteCoordinates.getX(), (float)absoluteCoordinates.getY());
            }

            previousAbsoluteCoordinates.setX(absoluteCoordinates.getX());
            previousAbsoluteCoordinates.setY(absoluteCoordinates.getY());
        }
    }

    void renderLevelLine() {
        if (this.dots.isEmpty()) return;

        Dot lastAbsoluteCoordinates = calcAbsCoordinates(this.dots.size() - 1);

        processingRef.strokeWeight(1);
        processingRef.stroke(this.levelLineCl.getRGB(), 130);

        processingRef.line(this.originX, (float)lastAbsoluteCoordinates.getY(),
                (float)lastAbsoluteCoordinates.getX(), (float)lastAbsoluteCoordinates.getY());
    }

    void renderLastDotValue() {
        if (this.dots.isEmpty()) return;

        Dot lastAbsoluteCoordinates = calcAbsCoordinates(this.dots.size() - 1);

        processingRef.fill(this.valueTextCl.getRGB());
        processingRef.textSize(this.textSize);
        if (this.integer) processingRef.text((int)(this.dots.get(this.dots.size() - 1).getY()), this.originX + this.textSize + 40f, (float)(lastAbsoluteCoordinates.getY() - (this.textSize - 4)));
        else processingRef.text((float)(this.dots.get(this.dots.size() - 1).getY()), this.originX + this.textSize + 40f, (float)(lastAbsoluteCoordinates.getY() - (this.textSize - 4)));
    }

    void renderAxisScale() {
        processingRef.fill(this.scaleTextCl.getRGB());
        processingRef.textSize(this.textSize);
        if (this.integer) {
            processingRef.text((int) (this.maxY + 0.25 * (this.maxY - this.minY)), this.originX + 5f, this.originY + this.textSize + 4f);
            processingRef.text((int) (this.minY), this.originX + 5f, this.originY + this.dimY - (this.textSize - 4f));
        }
        else {
            processingRef.text((float) (this.maxY + 0.25 * (this.maxY - this.minY)), this.originX + 5f, this.originY + this.textSize + 4f);
            processingRef.text((float) (this.minY), this.originX + 5f, this.originY + this.dimY - (this.textSize - 4f));
        }
    }

    void renderTitle() {
        processingRef.fill(this.titleTextCl.getRGB());
        processingRef.textSize(this.textSize);
        processingRef.text(this.title, this.originX + 4f, this.originY + this.textSize + 24f);
    }

    void render() {
        renderPlain();
        renderDotsLine();
        renderLevelLine();
        renderLastDotValue();
        if(renderScale) renderAxisScale();
        if(renderTitle) renderTitle();
    }

}