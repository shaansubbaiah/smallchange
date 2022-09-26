import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-preferences',
  templateUrl: './preferences.component.html',
  styleUrls: ['./preferences.component.scss']
})
export class PreferencesComponent implements OnInit {
  
  form: any;
  formValidity: Boolean = false;
  constructor(private router: Router) { }
  setPreferences() {
    let textarea = document.getElementsByTagName('textarea');
    let select = document.getElementsByTagName('select');
    let checkBox = document.getElementsByTagName('input');
    let Preferences = {
      "Investment Purpose": textarea[0].value,
      "Risk Tolerance": select[0].value,
      "Income Category": select[1].value,
      "Duration of Investments": select[2].value,
      "Checkbox for T&C": checkBox[0].checked
    }
    for (const [key, value] of Object.entries(Preferences)) {
      if (value === '' || value === false) {
        console.log("Form is invalid!");
        return;
      }
    }
    console.log(Preferences);
    alert("Your investment preferences have been successfully saved!")
    this.router.navigate(['Home']);
    return Preferences;
  }
  ngOnInit(): void {
    this.form = document.querySelector('.needs-validation');
    this.form.addEventListener('submit', (event:
      { preventDefault: () => void; stopPropagation: () => void; }) => {
      this.formValidity = this.form.checkValidity();
      if (!this.formValidity) {
        event.preventDefault();
        event.stopPropagation();
        let button = document.getElementsByTagName('button');
      }
      this.form.classList.add('was-validated')
    }, false)
  }
}