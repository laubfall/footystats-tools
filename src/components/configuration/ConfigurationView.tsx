import { ipcRenderer } from 'electron';
import { includes, size } from 'lodash';
import React, { useState } from 'react';
import { Button, Col, Form, Row } from 'react-bootstrap';
import { saveConfig } from '../../app/services/application/ConfigurationService';
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

  const openDirectoryDialog = (setter: (path: string) => void) => {
    ipcRenderer
      .invoke('open-directory-dialog')
      .then((value: Electron.OpenDialogReturnValue) => {
        if (value.canceled === false) {
          setter(value.filePaths[0]);
        }
        return null;
      })
      .catch((reason) => console.log(reason));
  };

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
              placeholder="/Pfad/Importverzeichnis"
              value={importDirectory}
              isInvalid={includes(
                invalidConfigurations,
                InvalidConfigurations.IMPORT_DIRECTORY_DOESNOT_EXIST
              )}
              disabled
            />
          </Col>
          <Col xs="auto">
            <Button
              onClick={() => openDirectoryDialog(setImportDirectory)}
              className="mb-2"
            >
              Submit
            </Button>
          </Col>
        </Row>
        <Row className="align-items-center">
          <Col xs="auto">
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
          </Col>
          <Col xs="auto">
            <Button
              onClick={() => openDirectoryDialog(setDatabaseDirectory)}
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
