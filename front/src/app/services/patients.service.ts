import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Patient} from "../model/patient.model";
import {Observable} from 'rxjs';

@Injectable({providedIn: "root"})
export class PatientsService {

  constructor(private http: HttpClient) {
  }

  getAllPatients(): Observable<Patient[]> {
    let host = environment.host;
    return this.http.get<Patient[]>(host + '/patient/all');
  }


  getSearchPatient(keyword:string) {
    let host = environment.host;
    return this.http.get<Patient[]>(host + '/patient?name_like='+keyword);
  }

  savePatient(patient :Patient): Observable<Patient>{
    let host = environment.host;
    return this.http.post<Patient>(host + '/patient/add',patient);
  }

  updatePatient(patient :Patient) {
    let host = environment.host;
    return this.http.put<Patient>(host + '/patient/'+patient.id,patient);
  }

  getPatient(id:number): Observable<Patient> {
    let host = environment.host;
    return this.http.get<Patient>(host + '/patient/'+id);
  }

}
