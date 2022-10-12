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

  public static colors = [
    '#f2d5cf',
    '#eebebe',
    '#f4b8e4',
    '#ca9ee6',
    '#e78284',
    '#ea999c',
    '#ef9f76',
    '#e5c890',
    '#a6d189',
    '#81c8be',
    '#99d1db',
    '#85c1dc',
    '#8caaee',
    '#babbf1',
  ];

  public static rng(seed = '') {
    let x = 0;
    let y = 0;
    let z = 0;
    let w = 0;

    function next() {
      const t = x ^ (x << 11);
      x = y;
      y = z;
      z = w;
      w ^= ((w >>> 19) ^ t ^ (t >>> 8)) >>> 0;
      return w / 0x100000000;
    }

    for (var k = 0; k < seed.length + 64; k++) {
      x ^= seed.charCodeAt(k) | 0;
      next();
    }

    return next;
  }

  public static getPseudoRandomColor(str: string) {
    const getRandom = CommonUtils.rng(str);
    let colorIndex = getRandom();
    let max = CommonUtils.colors.length;
    colorIndex = Math.abs(Math.floor(colorIndex * max));
    return this.colors[colorIndex];
  }
}
