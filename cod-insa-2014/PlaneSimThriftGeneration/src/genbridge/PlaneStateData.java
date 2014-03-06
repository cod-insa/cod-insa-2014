/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package genbridge;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

public enum PlaneStateData implements org.apache.thrift.TEnum {
  IDLE(1),
  MOVING(2),
  ATTACKING(3),
  AT_AIRPORT(4);

  private final int value;

  private PlaneStateData(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  public static PlaneStateData findByValue(int value) { 
    switch (value) {
      case 1:
        return IDLE;
      case 2:
        return MOVING;
      case 3:
        return ATTACKING;
      case 4:
        return AT_AIRPORT;
      default:
        return null;
    }
  }
}
