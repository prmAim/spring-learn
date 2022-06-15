import {Component, OnInit} from '@angular/core';
import {ProductService} from "../services/product.service";
import {Product} from "../model/product";
import {ActivatedRoute, convertToParamMap, Router} from "@angular/router";

@Component({
  selector: 'app-product-form',
  templateUrl: './product-form.component.html',
  styleUrls: ['./product-form.component.scss']
})
export class ProductFormComponent implements OnInit {

  // Двунаправленая привязка в представлении и контроллере
  product = new Product(null, "", 0);

  isError = false;
  errorMessage = "";

  constructor(private productService: ProductService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit(): void {
    // subscribe - подписака на события изменения параметров [params] машрута URL [route]
    // реактивный подход
    this.route.params.subscribe(param => {
      if (param['id'] != null) {
        this.productService.findByID(param['id'])
          .subscribe(res => {
            this.product = res;
          }, err => {
            console.error(err);
          })
      }
    })
  }

  /**
   * Сохранение объекта
   */
  save() {
    this.productService.save(this.product)
      .subscribe(res => {
        console.log(res)    //логирование
        this.isError = false;
        this.errorMessage = "";
        this.router.navigateByUrl('/product')
      }, err => {
        console.error(err);
        this.isError = true;
        this.errorMessage = err.error;
      })
  }
}
