import { Component, OnInit } from '@angular/core';
import {PatientsService} from "../../services/patients.service";
import {Patient} from "../../model/patient.model";
import {Observable, of} from "rxjs";
import {catchError, map, startWith} from "rxjs/operators";
import {AppDataState, DataStateEnum} from "../../state/patients.state";
import {Router} from "@angular/router";

@Component({
  selector: 'app-patients',
  templateUrl: './patients.component.html',
  styleUrls: ['./patients.component.css']
})
export class PatientsComponent implements OnInit {

  patients$:Observable<AppDataState<Patient[]>> |null=null;

  readonly DataStateEnum=DataStateEnum;

  constructor(private patientService:PatientsService,private router:Router) { }

  ngOnInit(): void {
  }

  onGetAllPatients() {
        this.patients$=this.patientService.getAllPatients()
          .pipe(
            map(data=> {
              console.log(data)
              return ({dataState: DataStateEnum.LOADED, data: data})
            }),
            startWith({dataState:DataStateEnum.LOADING}),
            catchError(err=>of({dataState:DataStateEnum.ERROR,errorMessage:err.message}))

          );

  }

  onSearch(dataForm: any) {
    this.patients$=this.patientService.getSearchPatient(dataForm.keyword)
      .pipe(
        map(data=> {
          console.log(data)
          return ({dataState: DataStateEnum.LOADED, data: data})
        }),
        startWith({dataState:DataStateEnum.LOADING}),
        catchError(err=>of({dataState:DataStateEnum.ERROR,errorMessage:err.message}))
      );
  }

  onNewPatient() {
    this.router.navigateByUrl("/newPatient");
  }

  onEdit(p: Patient) {
    this.router.navigateByUrl("/editPatient/"+p.id);
  }

}
