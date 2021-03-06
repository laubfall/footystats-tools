import { includes, size } from 'lodash';
import React, { useState, useEffect } from 'react';
import { Button, Col, Form, Row } from 'react-bootstrap';

import {
  loadConfig,
  saveConfig,
} from '../../app/services/application/ConfigurationService';
import INativeGuiService from '../../app/services/application/gui/INativeGuiService';
import IpcNativeGuiService from '../../app/services/application/gui/IpcNativeGuiService';
import Configuration, {
  InvalidConfigurations,
} from '../../app/types/application/Configuration';

// eslint-disable-next-line import/prefer-default-export
export const ConfigurationView = () => {
  const [importDirectory, setImportDirectory] = useState<string>('');

  const [databaseDirectory, setDatabaseDirectory] = useState<string>('');

  const [invalidConfigurations, setInvalidConfigurations] = useState<
    InvalidConfigurations[]
  >();

  useEffect(() => {
    // eslint-disable-next-line promise/catch-or-return
    loadConfig().then((cfg) => {
      setImportDirectory(cfg.importDirectory);
      setDatabaseDirectory(cfg.databaseDirectory);
      return cfg;
    });
  }, []);

  const saveConfiguration = () => {
    const config = new Configuration();
    config.importDirectory = importDirectory;
    config.databaseDirectory = databaseDirectory;
    const invalidConfs = config.validate();
    setInvalidConfigurations(invalidConfs);

    if (size(invalidConfs) === 0) {
      saveConfig(config);
    }
  };

  const nativeGuiService: INativeGuiService = new IpcNativeGuiService();

  return (
    <>
      <Form>
        <Row className="align-items-center">
          <Col md={6}>
            <Form.Label htmlFor="inlineFormInput" visuallyHidden>
              Pfad Importfiles
            </Form.Label>
            <Form.Control
              className="mb-2"
              id="inlineFormInput"
              placeholder="/Pfad/Importverzeichnis"
              value={importDirectory}
              isInvalid={includes(
                invalidConfigurations,
                InvalidConfigurations.IMPORT_DIRECTORY_DOESNOT_EXIST
              )}
              disabled
            />
            <Form.Text muted>
              Importdirectory for footystats csv files
            </Form.Text>
          </Col>
          <Col>
            <Button
              onClick={() =>
                nativeGuiService
                  .callOpenDialog()
                  .then((value: Electron.OpenDialogReturnValue) => {
                    if (value?.canceled === false) {
                      setImportDirectory(value.filePaths[0]);
                    }
                    return null;
                  })
              }
              className="mb-2"
            >
              Submit
            </Button>
          </Col>
        </Row>
        <Row className="align-items-center">
          <Col md={6}>
            <Form.Label htmlFor="inlineFormInput" visuallyHidden>
              Pfad Datenbankdateien
            </Form.Label>
            <Form.Control
              className="mb-2"
              id="inlineFormInput"
              placeholder="/Pfad/Datenbankverzeichnis"
              value={databaseDirectory}
              isInvalid={includes(
                invalidConfigurations,
                InvalidConfigurations.DATABASE_DIRECTORY_DOESNOT_EXIST
              )}
              disabled
            />
            <Form.Text muted>Directory where we store database files</Form.Text>
          </Col>
          <Col>
            <Button
              onClick={() => {
                nativeGuiService
                  .callOpenDialog()
                  .then((value: Electron.OpenDialogReturnValue) => {
                    if (value?.canceled === false) {
                      setDatabaseDirectory(value.filePaths[0]);
                    }
                    return null;
                  })
                  .catch((reason) => console.error(reason));
              }}
              className="mb-2"
            >
              Submit
            </Button>
          </Col>
        </Row>

        <Button variant="primary" onClick={saveConfiguration}>
          Submit
        </Button>
      </Form>
    </>
  );
};
