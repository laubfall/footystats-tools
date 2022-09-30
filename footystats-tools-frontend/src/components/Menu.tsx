import React from "react";
import { Container, Nav, Navbar } from "react-bootstrap";
import { useNavigate } from "react-router";
import translate from "../i18n/translate";

export const Menu = () => {
  const navigate = useNavigate();

  return (
    <Navbar bg="light" expand="lg">
      <Container>
        <Navbar.Brand href="#home">
          {translate("renderer.menu.brand")}
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
            <Nav.Link onClick={() => navigate("configuration")}>
              {translate("renderer.menu.item.configuration")}
            </Nav.Link>
            <Nav.Link onClick={() => navigate("matchList")}>
              {translate("renderer.menu.item.matchview")}
            </Nav.Link>
            <Nav.Link onClick={() => navigate("predictionQuality")}>
              {translate("renderer.menu.item.predictionqualityview")}
            </Nav.Link>
            <Nav.Link onClick={() => navigate("uploadmatchstats")}>
              {translate("renderer.menu.item.uploadmatchstats")}
            </Nav.Link>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};
