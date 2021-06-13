import { Menu } from './../enum/menu.enum';
import { Observable } from 'rxjs';
import { Injectable, EventEmitter } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class EventService {
  private sideMenuToggleEvent: EventEmitter<boolean>;
  private sideMenuSelectEvent: EventEmitter<Menu[]>;
  private isOpened = false;

  constructor() {
    this.sideMenuToggleEvent = new EventEmitter<boolean>();
    this.sideMenuSelectEvent = new EventEmitter<Menu[]>();
    this.sideMenuToggleState().subscribe((isOpened: boolean) => {
      this.isOpened = isOpened;
    });
  }

  setSideMenuOpen(isOpened: boolean): void {
    this.sideMenuToggleEvent.emit(isOpened);
  }

  toggleSideMenu(): void {
    this.sideMenuToggleEvent.emit(!this.isOpened);
  }

  sideMenuToggleState(): Observable<boolean> {
    return this.sideMenuToggleEvent.asObservable();
  }

  setSelectedSideMenu(selectedMenus: Menu[]): void {
    this.sideMenuSelectEvent.emit(selectedMenus);
  }

  sideMenuSelectedState(): Observable<Menu[]> {
    return this.sideMenuSelectEvent.asObservable();
  }
}
