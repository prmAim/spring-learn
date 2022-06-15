import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Page} from "../model/page";
import {Product} from "../model/product";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }

  public  findAll(){
    // The path http://localhost:8081/mvc-app/ is write in proxy-file [frontend/proxy.conf.json]
    return this.http.get<Page>('rest/v1/product/all');
  }

  public findByID(id: number){
    return this.http.get<Product>(`rest/v1/product/${id}/id`);
  }

  public save(product: Product){
    if (product.id == null){
      return this.http.post<Product>('rest/v1/product', product);
    } else {
      return this.http.put<Product>('rest/v1/product', product);
    }
  }

  /**
   * Удаление объекта
   */
  remove(id: number){
    return this.http.delete<Product>(`rest/v1/product/${id}`);
  }
}
