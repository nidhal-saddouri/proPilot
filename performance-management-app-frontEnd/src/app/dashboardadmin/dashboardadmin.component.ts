import { Component } from '@angular/core';
import { User, UsersService } from '../users.service';
import { FilterService } from '../filter.service';

@Component({
  selector: 'app-dashboardadmin',
  templateUrl: './dashboardadmin.component.html',
  styleUrls: ['./dashboardadmin.component.css']
})
export class DashboardadminComponent {
  users: User[]=[];
  constructor(private userService: UsersService, private filterService :FilterService){}

  getApprovedUsers(isApproved: boolean): void {
    this.filterService.setFilter(isApproved); // Transmet le filtre
  }
}
