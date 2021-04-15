import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {PatientsService} from "../../../services/patients.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-patient-add',
  templateUrl: './patient-add.component.html',
  styleUrls: ['./patient-add.component.css']
})
export class PatientAddComponent implements OnInit {

  patientFormGroup?: FormGroup;
  submitted: boolean = false;
  genderSelected: boolean = false;

  templateUnchecked = false;
  templateChecked = true;
  template = true;

  constructor(private fb: FormBuilder,
              private patientsService: PatientsService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.patientFormGroup = this.fb.group({
      lastName: ["", Validators.required],
      firstName: ["", Validators.required],
      dob: ["", Validators.required],
      gender: [this.template, Validators.required],
      address: [""],
      phone: [""]
    });
  }

  onSavePatient() {
    console.log('ngModel value', this.template);
    this.submitted = true;
    if (this.patientFormGroup?.invalid) return;
    this.patientsService.savePatient(this.patientFormGroup?.value)
      .subscribe(data => {
        alert("Success Saving patient");
        this.router.navigateByUrl("/patients");
      });
  }

  onCancelPatient() {
    this.router.navigateByUrl("/patients");
  }

}
