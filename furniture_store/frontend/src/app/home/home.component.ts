import { Component, OnInit } from '@angular/core';
import { HttpcomsService } from '../httpcoms.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  mainData = [{employee_name: "test"}];

  data: boolean = false;

  constructor(private httpcoms: HttpcomsService) { }

  ngOnInit(): void {

  }

  getData() {
    this.httpcoms.getData().subscribe((data: any) => {
      console.log(data);
      this.mainData = data;
      this.data = true;
    });
  }





}
