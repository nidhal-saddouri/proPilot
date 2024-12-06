import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../user.service';

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.css']
})
export class UpdateUserComponent implements OnInit {
  updateUserForm!: FormGroup;
  userId!: number;

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private route: ActivatedRoute,
   private router: Router
  ) {}

  ngOnInit(): void {
    this.userId = +this.route.snapshot.paramMap.get('id')!;
    this.initForm();
    this.loadUserData();
  }

  initForm() {
    this.updateUserForm = this.fb.group({
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      role: ['', [Validators.required]]
    });
  }

  loadUserData() {
    this.userService.getUserById(this.userId).subscribe(
      (user) => {
        this.updateUserForm.patchValue(user);
      },
      (error) => console.error(error)
    );
  }

  onSubmit() {
    if (this.updateUserForm.valid) {
      this.userService.updateUser(this.userId, this.updateUserForm.value).subscribe(
        (response) => {
          alert('Utilisateur mis à jour avec succès');
          this.router.navigate(['/users']); // Redirection correcte
        },
        (error) => console.error(error)
      );
    }
  }
}
