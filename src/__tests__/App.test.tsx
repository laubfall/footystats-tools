import React from 'react';
import '@testing-library/jest-dom';
import { render } from '@testing-library/react';
import { App } from '../App';

jest.mock('../app/services/application/Ipc2RendererService');

describe('App', () => {
  it('should render', () => {
    expect(render(<App />)).toBeTruthy();
  });
});
