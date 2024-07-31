import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { ChairGaleryComponent } from './chair-galery/chair-galery.component';
import { TableGaleryComponent } from './table-galery/table-galery.component';
import { ConectComponent } from './conect/conect.component';
import { LogInComponent } from './log-in/log-in.component';
import { PersonalLogInComponent } from './personal-log-in/personal-log-in.component';
import { UpdatePDComponent } from './update-p-d/update-p-d.component';
import { RegisterComponent } from './register/register.component';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';
import { PaymentComponent } from './payment/payment.component';
import { ItemComponent } from './item/item.component';
import { OrdersComponent } from './orders/orders.component';
import { StockComponent } from './stock/stock.component';

const routes: Routes = [
  {path: "", component: HomeComponent},
  {path: "chair-galery", component: ChairGaleryComponent},
  {path: "table-galery", component: TableGaleryComponent},
  {path: "conect", component: ConectComponent},
  {path: "log-in", component: LogInComponent},
  {path: "personal-log-in", component: PersonalLogInComponent},
  {path: "update-p-d", component: UpdatePDComponent},
  {path: "register", component: RegisterComponent},
  {path: "shopping-cart", component: ShoppingCartComponent},
  {path: "payment", component: PaymentComponent},
  {path: "item", component: ItemComponent},
  {path: "orders", component: OrdersComponent},
  {path: "stock", component: StockComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
