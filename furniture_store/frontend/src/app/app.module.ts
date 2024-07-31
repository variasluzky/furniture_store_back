import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http'

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { GaleryComponent } from './galery/galery.component';
import { ChairGaleryComponent } from './chair-galery/chair-galery.component';
import { TableGaleryComponent } from './table-galery/table-galery.component';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { ChairComponent } from './chair/chair.component';
import { TableComponent } from './table/table.component';
import { ConectComponent } from './conect/conect.component';
import { PersonalLogInComponent } from './personal-log-in/personal-log-in.component';
import { LogInComponent } from './log-in/log-in.component';
import { UpdatePDComponent } from './update-p-d/update-p-d.component';
import { RegisterComponent } from './register/register.component';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';
import { PaymentComponent } from './payment/payment.component';
import { ItemComponent } from './item/item.component';
import { OrdersComponent } from './orders/orders.component';
import { FooterComponent } from './footer/footer.component';
import { StockComponent } from './stock/stock.component';
import { ManageShopingCartComponent } from './manage-shoping-cart/manage-shoping-cart.component';
import { ManageClientsComponent } from './manage-clients/manage-clients.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    GaleryComponent,
    ChairGaleryComponent,
    TableGaleryComponent,
    NavBarComponent,
    ChairComponent,
    TableComponent,
    ConectComponent,
    PersonalLogInComponent,
    LogInComponent,
    UpdatePDComponent,
    RegisterComponent,
    ShoppingCartComponent,
    PaymentComponent,
    ItemComponent,
    OrdersComponent,
    FooterComponent,
    StockComponent,
    ManageShopingCartComponent,
    ManageClientsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
