/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */

import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

public enum Command implements org.apache.thrift.TEnum {
  MOVE_PLANE(1),
  BUILD_PLANE(2);

  private final int value;

  private Command(int value) {
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
  public static Command findByValue(int value) { 
    switch (value) {
      case 1:
        return MOVE_PLANE;
      case 2:
        return BUILD_PLANE;
      default:
        return null;
    }
  }
}
