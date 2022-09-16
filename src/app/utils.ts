import { Sort } from "@angular/material/sort";
import { MatTableDataSource } from "@angular/material/table";

export class CommonUtils {
  public static compare(a: number | string, b: number | string, isAsc: boolean) : number {
    return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
  }

  public static sortData<T>(data: any[], sort: Sort) {
    let sortedData : T[];

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
}
