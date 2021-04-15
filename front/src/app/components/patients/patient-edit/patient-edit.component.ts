import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {PatientsService} from "../../../services/patients.service";

@Component({
  selector: 'app-patient-edit',
  templateUrl: './patient-edit.component.html',
  styleUrls: ['./patient-edit.component.css']
})
export class PatientEditComponent implements OnInit {
  patientId: number;
  patientFormGroup?: FormGroup;
  private submitted: boolean = false;

  constructor(private activatedRoute: ActivatedRoute,
              private patientsService: PatientsService,
              private fb: FormBuilder,
              private router: Router) {
    this.patientId = activatedRoute.snapshot.params.id;
  }

  ngOnInit(): void {
    this.patientsService.getPatient(this.patientId)
      .subscribe(patient => {
        this.patientFormGroup = this.fb.group({
          id: [patient.id],
          lastName: [patient.lastName, Validators.required],
          firstName: [patient.firstName, Validators.required],
          dob: [patient.dob, Validators.required],
          gender: [patient.gender, Validators.required],
          address: [patient.address],
          phone: [patient.phone]
        })
      });
  }

  onUpdatePatient() {
    this.patientsService.updatePatient(this.patientFormGroup?.value)
      .subscribe(data => {
        alert("Success updated");
        this.router.navigateByUrl("/patients");
      });
  }

  onCancelPatient() {
    this.router.navigateByUrl("/patients");
  }
}
