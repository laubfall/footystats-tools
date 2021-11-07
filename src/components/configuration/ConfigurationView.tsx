import React from 'react';
import { Button, Col, Form, Row } from 'react-bootstrap';
import { remote } from 'electron';

// eslint-disable-next-line import/prefer-default-export
export const ConfigurationView = () => {
  const openDirectoryDialog = () => {
    const win = new remote.BrowserWindow();
    remote.dialog.showOpenDialog(win, {
      properties: ['openDirectory', 'multiSelections'],
    });
  };

  return (
    <>
      <Form>
        <Row className="align-items-center">
          <Col xs="auto">
            <Form.Label htmlFor="inlineFormInput" visuallyHidden>
              Pfad Importfiles
            </Form.Label>
            <Form.Control
              className="mb-2"
              id="inlineFormInput"
              placeholder="Jane Doe"
            />
          </Col>
          <Col xs="auto">
            <Button onClick={() => openDirectoryDialog()} className="mb-2">
              Submit
            </Button>
          </Col>
        </Row>
      </Form>
    </>
  );
};
