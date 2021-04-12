import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute} from "@angular/router";
import {PatientsService} from "../../../services/patients.service";

@Component({
  selector: 'app-patient-edit',
  templateUrl: './patient-edit.component.html',
  styleUrls: ['./patient-edit.component.css']
})
export class PatientEditComponent implements OnInit {
  patientId:number;
  patientFormGroup?:FormGroup;
  private submitted:boolean=false;

  constructor(private activatedRoute:ActivatedRoute,
              private patientsService:PatientsService,
              private fb:FormBuilder) {
    this.patientId=activatedRoute.snapshot.params.id;
  }

  ngOnInit(): void {
    this.patientsService.getPatient(this.patientId)
      .subscribe(patient=>{
        this.patientFormGroup=this.fb.group({
          lastName:[patient.lastName,Validators.required],
          firstName:[patient.firstName,Validators.required],
          dob:[patient.dob,Validators.required],
          kind:[patient.kind,Validators.required],
          address:[patient.address],
          phone:[patient.phone]
        })
      });
  }

  onUpdatePatient() {
    this.patientsService.updatePatient(this.patientFormGroup?.value)
      .subscribe(data=>{
        alert("Success updated");
      });
  }

}
