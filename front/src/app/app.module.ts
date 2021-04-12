import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import { PatientsComponent } from './components/patients/patients.component';
import { HomeComponent } from './components/home/home.component';
import { AppRoutingModule } from './app-routing.module';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {PatientAddComponent} from "./components/patients/patient-add/patient-add.component";
import {PatientEditComponent} from "./components/patients/patient-edit/patient-edit.component";



@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent,
    PatientsComponent,
    HomeComponent,
    PatientAddComponent,
    PatientEditComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
