import { Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';

export class CommonUtils {
  public static compare(
    a: number | string,
    b: number | string,
    isAsc: boolean
  ): number {
    return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
  }

  public static sortData<T>(data: any[], sort: Sort) {
    let sortedData: T[];

    if (!sort.active || sort.direction === '') {
      sortedData = data;
      return;
    }

    sortedData = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      return CommonUtils.compare(a[sort.active], b[sort.active], isAsc);
    });

    return new MatTableDataSource<T>(sortedData);
  }

  public static getUserDetail(key: string): string | null {
    let currentUserObj = localStorage.getItem('currentUser');
    if (currentUserObj === null) return null;

    return JSON.parse(currentUserObj)[key];
  }

  public static stringToColor(str: string) {
    var hash = 0;
    for (var i = 0; i < str.length; i++) {
      hash = str.charCodeAt(i) + ((hash << 5) - hash);
    }
    var colour = '#';
    for (var i = 0; i < 3; i++) {
      var value = (hash >> (i * 8)) & 0xff;
      colour += ('00' + value.toString(16)).substr(-2);
    }
    return colour;
  }
}
