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

  public static getTime(time : number) : string {
    return this.getTimeString(Date.now(), time);
}

public static getTimeString(time1 : number, time2 : number) : string {
    let t = +Math.abs(time1 - time2);
    if (t < 60000)
        return "just now";
    else if (t < 3600000)
        return t / 60000 + "m ago";
    else if (t < 86400000)
        return t / 3600000 + "h ago";
    else if (t < 604800000)
        return t / 86400000 + "d ago";
    else if (t < 2592000000)
        return t / 604800000 + "w ago";
    else if (t < 31536000000)
        return t / 2592000000 + "M ago";
    else
        return t / 31536000000 + "y ago";
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
