import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'snakeToTitle',
})
export class SnakeToTitlePipe implements PipeTransform {
  transform(value: string): string {
    // From https://stackoverflow.com/a/64489760
    let result = value
      .replace(/^[-_]*(.)/, (_, c) => c.toUpperCase())
      .replace(/[-_]+(.)/g, (_, c) => ' ' + c.toUpperCase());

    return result;
  }
}
