import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserNotApprovedComponent } from './user-not-approved.component';

describe('UserNotApprovedComponent', () => {
  let component: UserNotApprovedComponent;
  let fixture: ComponentFixture<UserNotApprovedComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserNotApprovedComponent]
    });
    fixture = TestBed.createComponent(UserNotApprovedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
