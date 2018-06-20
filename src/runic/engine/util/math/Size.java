package runic.engine.util.math;

/**
 * 
 * This class is the base set for the different dimensions of shapes
 * If the shape is a Rectangle/Square, use the other constructors, this sets the width and height separate (the width and height or the length of a Square)
 * 
*/
public class Size {

  private int width, height;

  public Size(int width, int height) {
    this.width = width;
    this.height = height;
  }

  public Size(int length) {
    this.width = length;
    this.height = length;
  }

  public Size(Size dimension) {
    this.width = dimension.getWidth();
    this.height = dimension.getHeight();
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public void setSize(int width, int height) {
      this.width = width;
      this.height = height;
  }

  public void setSize(Size dimension) {
    this.width = dimension.getWidth();
    this.height = dimension.getHeight();
  }

}
