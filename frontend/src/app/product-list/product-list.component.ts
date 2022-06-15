import {Component, OnInit} from '@angular/core';
import {Product} from "../model/product";
import {ProductService} from "../services/product.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss']
})
export class ProductListComponent implements OnInit {

  products: Product[] = [];

  constructor(private productService: ProductService,
              private router: Router) {
  }

  ngOnInit(): void {
    // подписка на событие subscribe (реактивный метод работы [фоновый режим])
    this.productService.findAll().subscribe(res => {
      this.products = res.content;
    }, err => {
      console.error(err)
    })
  }

  delete(id: number | null) {
    if (id != null) {
      this.productService.remove(id)
        .subscribe(res => {
          console.log(res)    //логирование
          this.productService.findAll().subscribe(res => {
            this.products = res.content;
          }, err => {
            console.error(err)
          })
        })
    }
  }
}
