import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { SharedModule } from './shared/shared.module';
import { CoreModule } from './core/core.module';
import { FeaturesModule } from './features/features.module';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
// import {MatToolbarModule} from '@angular/material/toolbar';

// import { LoginPageComponent } from "./components/login-page/login-page.component";
// import {MatFormFieldModule } from '@angular/material/form-field';
// import {MatInputModule} from '@angular/material/input';
// import {MatIconModule} from '@angular/material/icon';
// import {MatButtonModule } from '@angular/material/button';

// import { MatCardModule } from '@angular/material/card';

// import { NavbarComponent } from './components/navbar/navbar.component';
// import { LoginFormComponent } from './login-form/login-form.component';

@NgModule({
  declarations: [AppComponent],
  imports: [
    CoreModule,
    BrowserModule,
    AppRoutingModule,
    SharedModule,
    FeaturesModule,
    // BrowserAnimationsModule,
    // MatFormFieldModule,
    // MatInputModule,
    // MatIconModule,
    // MatButtonModule,
    // MatCardModule,
    // MatToolbarModule
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
