import React from 'react';
import '@testing-library/jest-dom';
import { render } from '@testing-library/react';
import { App } from '../App';
import TestUtils from './TestUtils';

describe('App', () => {
  it('should render', () => {
    expect(render(<App />)).toBeTruthy();
  });
});
