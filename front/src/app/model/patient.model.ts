export class Patient {
  id: number;
  /**
   * Last Name
   */
  lastName: String;
  /**
   * Fist Name
   */
  firstName: String;
  /**
   * Date of Birthday
   */
  dob: Date;
  /**
   * kind of person
   * True = man
   * False = women
   */
  gender: boolean;
  /**
   * Patient address
   */
  address: String;
  /**
   * patient phone
   */
  phone: String;
}
