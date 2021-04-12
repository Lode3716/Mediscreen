import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {PatientsService} from "../../../services/patients.service";

@Component({
  selector: 'app-patient-add',
  templateUrl: './patient-add.component.html',
  styleUrls: ['./patient-add.component.css']
})
export class PatientAddComponent implements OnInit {

  patientFormGroup?:FormGroup;
  submitted:boolean=false;

  constructor(private fb:FormBuilder, private patientsService:PatientsService) { }

  ngOnInit(): void {
    this.patientFormGroup=this.fb.group({
      lastName:["",Validators.required],
      firstName:["",Validators.required],
      dob:["",Validators.required],
      kind:[false,Validators.required],
      address:[""],
      phone:[""]
    });
  }

  onSavePatient() {
    this.submitted=true;
    if(this.patientFormGroup?.invalid) return;
    this.patientsService.savePatient(this.patientFormGroup?.value)
      .subscribe(data=>{
        alert("Success Saving patient");
      });
  }

}
