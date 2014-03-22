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
  GOING_TO(2),
  FOLLOWING(3),
  ATTACKING(4),
  LANDING(5),
  AT_AIRPORT(6),
  DEAD(7);

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
        return GOING_TO;
      case 3:
        return FOLLOWING;
      case 4:
        return ATTACKING;
      case 5:
        return LANDING;
      case 6:
        return AT_AIRPORT;
      case 7:
        return DEAD;
      default:
        return null;
    }
  }
}