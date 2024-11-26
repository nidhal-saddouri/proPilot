import { Component } from '@angular/core';
import { User, UsersService } from '../users.service';

@Component({
  selector: 'app-dashboardadmin',
  templateUrl: './dashboardadmin.component.html',
  styleUrls: ['./dashboardadmin.component.css']
})
export class DashboardadminComponent {
  showAddUserForm: boolean = false;
  showUserForm: boolean  = false;

  users: User[]=[];
  constructor(private userService: UsersService){}
  toggleAddUserForm() {
    this.showUserForm = false;

    this.showAddUserForm = true;
  }


}
